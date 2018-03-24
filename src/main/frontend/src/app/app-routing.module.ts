import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {TasksComponent} from "./tasks/tasks.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {RegisterComponent} from "./auth/register/register.component";
import {LoginComponent} from "./auth/login/login.component";
import {AnonymousAccessGuardService} from "./auth-guards/anonymous-access-guard.service";

@NgModule({
    imports: [
        RouterModule.forRoot([
            {path: '', component: TasksComponent},
            {path: 'register', component: RegisterComponent, canActivate: [AnonymousAccessGuardService]},
            {path: 'login', component: LoginComponent, canActivate: [AnonymousAccessGuardService]},
            {path: '**', component: PageNotFoundComponent}
        ])
    ],
    providers: [AnonymousAccessGuardService],
    exports: [RouterModule]
})
export class AppRoutingModule {
}