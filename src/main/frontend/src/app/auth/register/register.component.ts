import {Component, OnInit} from "@angular/core";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as HttpStatus from "http-status-codes";

import {RegisterService} from "./register.service";
import {UserResponse} from "../../domain/response/user-response";
import {RegisterRequest} from "../../domain/request/register-request";
import {ErrorResponse} from "../../error/error-response";

function passwordMatcher(c: AbstractControl): { [key: string]: boolean } | null {
    let passwordControl = c.get('password');
    let passwordConfirmControl = c.get('confirmPassword');

    if (passwordControl.pristine || passwordConfirmControl.pristine) {
        return null;
    }

    if (passwordControl.value === passwordConfirmControl.value) {
        return null;
    }
    return {'passwordMatch': true};
}

@Component({
    selector: 'app-user-register',
    templateUrl: 'register.component.html',
    styleUrls: ['register.component.css']
})
export class RegisterComponent implements OnInit {

    userForm: FormGroup;
    user: RegisterRequest = new RegisterRequest();
    userResponse: UserResponse;
    responseStatus: number;
    responseError: ErrorResponse;

    constructor(private userRegisterService: RegisterService, private fb: FormBuilder) {
    }

    ngOnInit(): void {
        this.userForm = this.fb.group({
            username: ['', [Validators.required, Validators.pattern('^[a-z0-9_-]{6,32}$')]],
            email: ['', [Validators.required, Validators.email]],
            passwordGroup: this.fb.group({
                password: ['', [Validators.required, Validators.pattern('((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})')]],
                confirmPassword: ['', Validators.required]
            }, {validator: passwordMatcher})
        });
    }

    registerUser() {
        this.user.username = this.userForm.value.username;
        this.user.password = this.userForm.value.passwordGroup.password;
        this.user.email = this.userForm.value.email;
        this.userRegisterService.registerNewUser(this.user)
            .subscribe(
                res => {
                    this.responseStatus = res.status;
                    this.userResponse = res.json();
                    this.userForm.reset();
                },
                err => {
                    this.responseStatus = err.status;
                    this.responseError = err.json();
                });
    }

    isCreatedStatus(): boolean {
        return this.responseStatus === HttpStatus.CREATED ? true : false;
    }
}