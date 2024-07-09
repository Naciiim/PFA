import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomepageFrontComponent} from "./homepage-front/homepage-front.component";


const routes: Routes = [
  { path: '', component: HomepageFrontComponent },
  { path: 'export/:type', component: HomepageFrontComponent },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
