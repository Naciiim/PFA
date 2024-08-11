import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomepageFrontComponent} from "./components/homepage-front/homepage-front.component";
import {MouvementComponent} from "./components/mouvement/mouvement.component";
import {PostingcreComponent} from "./components/postingcre/postingcre.component";


const routes: Routes = [
  { path: '', component: HomepageFrontComponent },
  { path: 'export/:type', component: HomepageFrontComponent },
  { path: 'mouvement', component: MouvementComponent },
  {path: 'postingcre',component: PostingcreComponent},
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
