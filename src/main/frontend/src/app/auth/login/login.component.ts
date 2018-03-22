import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as HttpStatus from "http-status-codes";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../../domain/request/login-request";
import {ErrorResponse} from "../../error/error-response";
import {CookieService} from 'angular2-cookie/services/cookies.service';
import {UserResponse} from "../../domain/response/user-response";

@Component({
    selector: 'app-user-login',
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {

    private USERNAME_COOKIE: string = 'USERNAME';

    loginForm: FormGroup;
    loginData: LoginRequest = new LoginRequest();
    responseError: ErrorResponse;
    responseStatus: number;
    userResponse: UserResponse = new UserResponse();

    constructor(private userLoginService: LoginService,
                private fb: FormBuilder,
                private router: Router, private cookieService: CookieService) {
    }

    ngOnInit(): void {
        this.loginForm = this.fb.group({
            username: ['', [Validators.required, Validators.pattern('^[a-z0-9_-]{6,32}$')]],
            password: ['', [Validators.required, Validators.pattern('((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})')]]
        });
    }

    loginUser() {
        this.loginData = this.loginForm.getRawValue();
        this.userLoginService.loginUser(this.loginData)
            .subscribe(
                res => {
                    this.userLoginService.getLoggedUsername().subscribe(res => {
                        this.userResponse = res.json()
                        this.cookieService.put(this.USERNAME_COOKIE, this.userResponse.username);
                    });

                    this.responseStatus = res.status;
                    this.loginForm.reset();
                    this.router.navigate(['/'])
                },
                err => {
                    this.responseStatus = err.status;
                    this.responseError = err.json();
                });
    }


    isOkStatus(): boolean {
        return this.responseStatus === HttpStatus.OK ? true : false;
    }
}