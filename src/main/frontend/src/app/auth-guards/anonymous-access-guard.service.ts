import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {LoginService} from "../auth/login/login.service";

@Injectable()
export class AnonymousAccessGuardService implements CanActivate {

    constructor(private router: Router, private loginService: LoginService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

        return this.isAnonymous();
    }

    private isAnonymous(): Observable<boolean> {
        return this.loginService.isAuthenticated()
                   .map(authenticated => {
                       this.navigateToHomePage(authenticated);
                       return !authenticated;
                   });
    }

    private navigateToHomePage(authenticated: any) {
        if (authenticated)
            this.router.navigate(['/']);
    }
}
