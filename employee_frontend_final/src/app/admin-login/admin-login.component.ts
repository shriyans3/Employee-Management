import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  adminName: string = '';
  adminPassword: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const loginPayload = {
      adminName: this.adminName,
      adminPassword: this.adminPassword
    };

    this.http.post('http://localhost:8080/api/v1/admin/login', loginPayload, { responseType: 'text' })
      .subscribe(
        (response) => {
          console.log('Login successful:', response);
          // Navigate to admin dashboard
          alert("login successful");
          this.router.navigate(['/home']);
        },
        (error) => {
          console.error('Login failed:', error);
          alert('Invalid credentials. Please try again.');
        }
      );
  }
}
