import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import {HandleError} from "../../error/handle-error";

@Injectable()
export class LogoutService {
    private logoutURL: string = 'api/logout';

    constructor(private http: Http, private handleError: HandleError) {
    }

    logoutUser() {
        return this.http
            .post(this.logoutURL, null)
            .catch(err => this.handleError.handleError(err));
    }
}