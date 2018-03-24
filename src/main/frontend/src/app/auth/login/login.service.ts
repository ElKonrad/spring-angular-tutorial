import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";

import {LoginRequest} from "../../domain/request/login-request";
import {HandleError} from "../../error/handle-error";

@Injectable()
export class LoginService {

    private loginURL: string = 'api/login';
    private userURL: string = 'api/users';

    constructor(private http: Http, private handleError: HandleError) {
    }

    loginUser(loginRequest: LoginRequest) {
        return this.http.post(this.loginURL, loginRequest)
                   .catch(err => this.handleError.handleError(err));
    }

    isAuthenticated() {
        return this.http.get(this.userURL + "/authentication")
                   .map(value => value.json());
    }

    getLoggedUsername() {
        return this.http.get(this.userURL + "/loggedUsername")
                   .catch(err => this.handleError.handleError(err));
    }
}