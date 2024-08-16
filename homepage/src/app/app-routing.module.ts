import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostingsComponent} from "./components/postings/postings.component";
import {MouvementComponent} from "./components/mouvement/mouvement.component";
import {PostingcreComponent} from "./components/postingcre/postingcre.component";


const routes: Routes = [
  { path: '', component: PostingsComponent },
  { path: 'export/:type', component: PostingsComponent },
  { path: 'mouvement', component: MouvementComponent },
  {path: 'postingcre',component: PostingcreComponent},
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
