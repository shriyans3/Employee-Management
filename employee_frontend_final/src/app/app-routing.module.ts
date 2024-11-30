import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { ShowDetailsComponent } from './show-details/show-details.component';
import { HomeComponent } from './home/home.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component'; // Import AdminDashboardComponent

const routes: Routes = [
  { path: 'show-all-employees', component: EmployeeListComponent },
  { path: 'add-employee', component: AddEmployeeComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'updating-by-id/:id', component: UpdateEmployeeComponent },
  { path: 'details-of-employee/:id', component: ShowDetailsComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: AdminLoginComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent }, // Add route for Admin Dashboard
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
