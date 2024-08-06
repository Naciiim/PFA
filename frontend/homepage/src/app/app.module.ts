import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatDialogModule } from '@angular/material/dialog';
import {FormsModule} from "@angular/forms";
import { MatIconModule } from '@angular/material/icon';
import {ExportService} from "./services/export.service";
import {PostingService} from "./services/posting.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HomepageFrontComponent} from "./components/homepage-front/homepage-front.component";
import {DefaultPostingsComponent} from "./components/default-postings/default-postings-component";
import {TransactionPostingsComponent} from "./components/transaction-postings/transaction-postings-component";
import {PostingDetailsComponent} from "./components/posting-details/posting-details.component";
import { SidebarComponent } from './components/sidebar/sidebar.component';
import {DefaultMovementsComponent} from "./components/default-mouvements/default-movements.component";
import { MouvementDetailsComponent } from './components/mouvement-details/mouvement-details.component';
import {MouvementComponent} from "./components/mouvement/mouvement.component";
import {RouterModule} from "@angular/router";
import { TransactionMouvementComponent } from './components/transaction-mouvement/transaction-mouvement.component';



@NgModule({
  declarations: [
    AppComponent,
    HomepageFrontComponent,
    DefaultPostingsComponent,
    TransactionPostingsComponent,
    PostingDetailsComponent,
    SidebarComponent,
    DefaultMovementsComponent,
    MouvementDetailsComponent,
    MouvementComponent,
    TransactionMouvementComponent

  ],
    imports: [
        BrowserModule,
        MatIconModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        MatDialogModule,
      RouterModule,
      BrowserAnimationsModule
    ],
  providers: [PostingService, ExportService],
  bootstrap: [AppComponent]
})
export class AppModule { }
