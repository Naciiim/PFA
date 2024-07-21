import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatDialogModule } from '@angular/material/dialog';
import {HomepageFrontComponent} from "./homepage-front/homepage-front.component";
import {FormsModule} from "@angular/forms";
import { DefaultPostingsComponent } from './default-postings/default-postings-component';
import {TransactionPostingsComponent} from "./transaction-postings/transaction-postings-component";
import {ExportService} from "./services/export.service";
import {PostingService} from "./services/posting.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {PostingDetailsComponent} from "./posting-details/posting-details.component";



@NgModule({
  declarations: [
    AppComponent,
    HomepageFrontComponent,
    DefaultPostingsComponent,
    TransactionPostingsComponent,
    PostingDetailsComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
       HttpClientModule,
       MatDialogModule,
       BrowserAnimationsModule
    ],
  providers: [PostingService, ExportService],
  bootstrap: [AppComponent]
})
export class AppModule { }
