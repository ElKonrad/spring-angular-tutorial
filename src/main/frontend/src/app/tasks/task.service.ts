import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {Task} from "./task.model";
import {EventEmitter, Injectable} from "@angular/core";
import {HandleError} from "../error/handle-error";

@Injectable()
export class TaskService {

    onTaskAdded = new EventEmitter<Task>();

    constructor(private http: Http, private handleError: HandleError) {
    }

    getTasks() {
        return this.http.get('/api/tasks')
                   .map(
                       (response: Response) => {
                           return response.json();
                       }
                   )
                   .catch(err => this.handleError.handleError(err))
    }

    addTask(task: Task) {
        return this.http.post('/api/tasks', task)
                   .map(
                       (response: Response) => {
                           return response.json();
                       }
                   )
                   .catch(err => this.handleError.handleError(err))
    }

    saveTask(task: Task, checked: boolean) {
        // we are updating the task to what the value of checked is
        task.completed = checked;
        return this.http.post('/api/tasks', task)
                   .map(
                       (response: Response) => {
                           return response.json();
                       }
                   )
                   .catch(err => this.handleError.handleError(err))
    }

}