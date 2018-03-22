import {Component, OnInit} from "@angular/core";
import {LogoutService} from "../../auth/logout/logout.service";
import {Router} from "@angular/router";
import {CookieService} from "angular2-cookie/core";

@Component({
    selector: 'app-header',
    templateUrl: 'header.component.html',
    styleUrls: ['header.component.css']
})
export class HeaderComponent implements OnInit {

    private USERNAME_COOKIE: string = 'USERNAME';

    constructor(private router: Router, private logoutService: LogoutService, private cookieService: CookieService) {
    }

    ngOnInit() {
        this.initializeCookieHeader();
    }

    initializeCookieHeader() {
        if (!this.ifUsernameCookieExists())
            this.cookieService.remove(this.USERNAME_COOKIE);
    }

    onLogout() {
        this.logoutService.logoutUser().subscribe(res => this.cookieService.remove(this.USERNAME_COOKIE));
        this.router.navigate(['/login']);
    }

    getUsername(): string {
        return this.cookieService.get(this.USERNAME_COOKIE);
    }

    ifUsernameCookieExists() {
        return this.cookieService.get(this.USERNAME_COOKIE) ? true : false;
    }
}