import {Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {Injectable} from '@angular/core';
import * as HttpStatus from 'http-status-codes';
import {Router} from "@angular/router";

@Injectable()
export class HandleError {

    constructor(private router: Router){}

    handleError(error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);

        if (error.status == HttpStatus.UNAUTHORIZED) {
            this.router.navigate(['/login']);
        }

        return Observable.throw(error);
    }
}