import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HomepageFrontComponent} from "./homepage-front/homepage-front.component";
import {FormsModule} from "@angular/forms";
import { DefaultPostingsComponent } from './default-postings/default-postings-component';
import {TransactionPostingsComponent} from "./transaction-postings/transaction-postings-component";
import {ExportService} from "./services/export.service";
import {PostingService} from "./services/posting.service";



@NgModule({
  declarations: [
    AppComponent,
    HomepageFrontComponent,
    DefaultPostingsComponent,
    TransactionPostingsComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
       HttpClientModule
    ],
  providers: [PostingService, ExportService],
  bootstrap: [AppComponent]
})
export class AppModule { }
