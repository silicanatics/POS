import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenuComponent } from './components/menu/menu.component';
import { MatButtonModule } from '@angular/material/button';
import {A11yModule} from '@angular/cdk/a11y';
import {ClipboardModule} from '@angular/cdk/clipboard';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatChipsModule} from '@angular/material/chips';
import {MatStepperModule} from '@angular/material/stepper';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material/dialog';
import {MatDividerModule} from '@angular/material/divider';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTreeModule} from '@angular/material/tree';
import {OverlayModule} from '@angular/cdk/overlay';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './components/login/login/login.component';
import { InvoiceComponent } from './components/reusable-components/invoice/invoice.component';
import { ClientsComponent } from './components/clients/clients/clients.component';
import { ItemPurchaseComponent } from './components/ItemPurchase/item-purchase/item-purchase.component';
import { MenuManagerComponent } from './components/menu-manager/menu-manager/menu-manager.component';
import { DashboardComponent } from './components/dashboard/dashboard/dashboard.component';
import { UsersComponent } from './components/users/users/users.component';
import { LocalStorageService } from './services/localstore/LocalStorageService';
import { JWTTokenService } from './services/localstore/JWTTokenService';
import { AuthInterceptor } from './services/interceptor/auth.interceptor';
import { ErrorInterceptor } from './services/errorinterceptor/ErrorInterceptor';
import { DefaultComponent } from './components/default_page/default/default.component';
import { LogoutComponent } from './components/logout/logout/logout.component';
import { ChartsModule, DropdownModule, MDBBootstrapModule, TableModule } from 'angular-bootstrap-md';
import { MenuSearchComponent } from './components/reusable-components/menu_search/menu-search/menu-search.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BarcodeScannerLivestreamModule } from 'ngx-barcode-scanner';
import { NgxBarcodeModule } from 'ngx-barcode';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { GoogleMapsModule } from '@angular/google-maps';
import { NewItemModalComponent } from './components/menu/NewMenuItem/new-item-modal/new-item-modal.component';
import { ReportsComponent } from './components/reports/reports/reports.component';
import { MenuSavedModalComponent } from './components/modals/menu-manager/menu-saved-modal/menu-saved-modal.component';
import { NgxPrintModule } from 'ngx-print';
import { ItemCountModalComponent } from './components/menu/modals/item-count-modal/item-count-modal.component';
import { GeneralItemModalComponent } from './components/menu/modals/general-item-modal/general-item-modal.component';
import { ItemsSoldComponent } from './components/reports/reports/items-sold/items-sold.component';
import { ConfigService } from './services/system_config/config.service';
import { AccountantReportComponent } from './components/reports/reports/accountant/accountant-report/accountant-report.component';
import { StockCaptureComponent } from './components/stock/stock-capture/stock-capture.component';
import { NewStockItemModalComponent } from './components/menu/modals/new-stock-item-modal/new-stock-item-modal.component';
import { ButtonModule } from 'primeng/button';
import { ItemSearchModalComponent } from './components/menu/modals/item-search-modal/item-search-modal.component';
import { FileUploadComponent } from './components/reusable-components/FileUpload/file-upload.component';
import { ReprintKitchenSlipModalComponent } from './components/menu/modals/reprint-kitchen-slip-modal/reprint-kitchen-slip-modal.component';





declare var $: any;
console.log(`jQuery version: ${$.fn.jquery}`);

export const configFactory = (configService: ConfigService) => {
  return () => configService.loadConfig();
};

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    LoginComponent,
    InvoiceComponent,
    ClientsComponent,
    ItemPurchaseComponent,
    MenuManagerComponent,
    DashboardComponent,
    UsersComponent,
    DefaultComponent,
    LogoutComponent,
    MenuSearchComponent,
    NewItemModalComponent,
    ReportsComponent,
    MenuSavedModalComponent,
    ItemCountModalComponent,
    GeneralItemModalComponent,
    ItemsSoldComponent,
    AccountantReportComponent,
    StockCaptureComponent,
    NewStockItemModalComponent,
    ItemSearchModalComponent,
    FileUploadComponent,
    ReprintKitchenSlipModalComponent
        
  ],
  imports: [
    BrowserAnimationsModule,
    TableModule,
    DropdownModule,
    ButtonModule,
    NgxPrintModule,
    GoogleMapsModule,
    NgxExtendedPdfViewerModule,
    MDBBootstrapModule.forRoot(),
    ReactiveFormsModule,
    PdfViewerModule,
    NgbModule,
    HttpClientModule,
    BrowserModule,
    BarcodeScannerLivestreamModule,
    NgxBarcodeModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSliderModule,
    A11yModule,
    ClipboardModule,
    CdkStepperModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    OverlayModule,
    PortalModule,
    ScrollingModule,
    FormsModule,
    ChartsModule
  ],
  exports: [
    ClientsComponent,
  ],
  providers: [        
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },  
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },  
    {
      provide: APP_INITIALIZER,
      useFactory: configFactory,
      deps: [ConfigService],
      multi: true
    },
    LocalStorageService,
    JWTTokenService
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
