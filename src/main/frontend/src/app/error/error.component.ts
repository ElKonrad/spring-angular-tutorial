import {Component, Input, OnInit} from "@angular/core";
import {ErrorResponse} from "./error-response";

@Component({
    selector: 'app-error',
    templateUrl: 'error.component.html',
})
export class ErrorComponent implements OnInit {

    @Input()
    errorResponse: ErrorResponse;

    ngOnInit(): void {
    }
}