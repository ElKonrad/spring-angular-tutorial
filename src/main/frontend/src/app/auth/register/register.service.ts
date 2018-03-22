import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {RegisterRequest} from "../../domain/request/register-request";
import {HandleError} from "../../error/handle-error";

@Injectable()
export class RegisterService {
    private registerURL: string = 'api/users';

    constructor(private http: Http, private handleError: HandleError) {
    }

    registerNewUser(user: RegisterRequest) {
        return this.http.post(this.registerURL, user)
            .catch(err => this.handleError.handleError(err));
    }
}