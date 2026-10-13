import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard/dashboard.component';
import { DefaultComponent } from './components/default_page/default/default.component';
import { LoginComponent } from './components/login/login/login.component';
import { LogoutComponent } from './components/logout/logout/logout.component';
import { MenuManagerComponent } from './components/menu-manager/menu-manager/menu-manager.component';
import { MenuComponent } from './components/menu/menu.component';
import { ReportsComponent } from './components/reports/reports/reports.component';
import { StockCaptureComponent } from './components/stock/stock-capture/stock-capture.component';
import { UsersComponent } from './components/users/users/users.component';
import { AuthGuardService } from './services/AuthGuard/auth-guard.service';

const routes: Routes = [
  {path:'',
  redirectTo: '/login',
  pathMatch: 'full'},
  {path:'default',
  component:DefaultComponent},
  {path:'login',
  component:LoginComponent},
  {path:'logout',
  component:LogoutComponent},
  {path:'dashboard',
  component:DashboardComponent},
  {path:'purchases', 
  component:MenuComponent},
  {path:'menumanager', 
  component:MenuManagerComponent},
  {path:'users', 
  component:UsersComponent},
  {path:'reports', 
  component:ReportsComponent},
  {path:'stockcapture', 
  component:StockCaptureComponent}
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
