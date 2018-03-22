import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import {TasksComponent} from "./tasks/tasks.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {RegisterComponent} from "./auth/register/register.component";
import {LoginComponent} from "./auth/login/login.component";

@NgModule({
    imports: [
        RouterModule.forRoot([
            { path: '', component: TasksComponent },
            { path: 'register', component: RegisterComponent },
            { path: 'login', component: LoginComponent },
            { path: '**', component: PageNotFoundComponent }
        ])
    ],
    providers: [],
    exports: [ RouterModule ]
})
export class AppRoutingModule { }