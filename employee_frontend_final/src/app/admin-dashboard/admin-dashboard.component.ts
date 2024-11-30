import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  totalEmployees: number = 0; // Total employees
  salaryRanges: any[] = []; // Salary range data
  departmentData: any[] = []; // Department distribution data
  departmentSalaries: any[] = [];

  @ViewChild('barChart', { static: true }) barChart!: ElementRef<HTMLCanvasElement>;
  @ViewChild('departmentPieChart', { static: true }) departmentPieChart!: ElementRef<HTMLCanvasElement>;
  @ViewChild('departmentSalaryDoughnutChart', { static: true }) departmentSalaryDoughnutChart!: ElementRef<HTMLCanvasElement>;

  constructor(private http: HttpClient) {
    // Register Chart.js components
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.fetchAnalytics();
    this.fetchDepartmentAnalytics();
    this.fetchDepartmentSalaries();
  }

  // Fetch salary range data for the bar chart
  fetchAnalytics() {
    this.http.get<any>('http://localhost:8080/api/v1/analytics').subscribe(data => {
      this.totalEmployees = data.totalEmployees;
      this.salaryRanges = data.salaryRanges;

      // Prepare data for the salary bar chart
      const labels = this.salaryRanges.map((item: any) => item[0]);
      const counts = this.salaryRanges.map((item: any) => item[1]);
      this.renderBarChart(labels, counts);
    });
  }

  // Fetch department distribution data for the pie chart
  fetchDepartmentAnalytics() {
    this.http.get<any[]>('http://localhost:8080/api/v1/department-analytics').subscribe(data => {
      this.departmentData = data;

      // Prepare data for the pie chart
      const labels = this.departmentData.map((item: any) => item.department || 'Unknown'); // Handle null departments
      const counts = this.departmentData.map((item: any) => item.count);

      this.renderDepartmentPieChart(labels, counts);
    });
  }
  fetchDepartmentSalaries() {
    this.http.get<any[]>('http://localhost:8080/api/v1/department-salary').subscribe(data => {
      this.departmentSalaries = data;

      // Prepare data for the doughnut chart
      const labels = this.departmentSalaries.map((item: any) => item.department || 'Unknown'); // Handle null departments
      const totals = this.departmentSalaries.map((item: any) => item.totalSalary);

      this.renderDepartmentSalaryDoughnutChart(labels, totals);
    });
  }
  // Render salary range bar chart
  renderBarChart(labels: string[], counts: number[]) {
    const ctx = this.barChart.nativeElement.getContext('2d')!;
    const barColors = ['#007bff', '#28a745', '#ffc107', '#dc3545']
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Number of Employees by Salary Range',
            data: counts,
            backgroundColor: barColors.slice(0, counts.length),
            borderColor: '#0056b3',
            borderWidth: 1,
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          }
        },
        scales: {
          x: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Salary Ranges'
            }
          },
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Number of Employees'
            }
          }
        }
      }
    });
  }

  // Render department distribution pie chart
  renderDepartmentPieChart(labels: string[], counts: number[]) {
    const ctx = this.departmentPieChart.nativeElement.getContext('2d')!;
    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Employee Distribution by Department',
            data: counts,
            backgroundColor: [
              '#007bff',
              '#28a745',
              '#ffc107',
              '#dc3545',
              '#17a2b8',
              '#6f42c1',
              '#fd7e14'
            ],
            borderColor: '#ffffff',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            callbacks: {
              label: (context) => {
                const label = context.label || '';
                const value = context.raw || 0;
                return `${label}: ${value} Employees`;
              }
            }
          }
        }
      }
    });
  }
  // Render department salary doughnut chart
  renderDepartmentSalaryDoughnutChart(labels: string[], totals: number[]) {
    const ctx = this.departmentSalaryDoughnutChart.nativeElement.getContext('2d')!;
    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Total Salary by Department',
            data: totals,
            backgroundColor: [
              '#007bff',
              '#28a745',
              '#ffc107',
              '#dc3545',
              '#17a2b8',
              '#6f42c1',
              '#fd7e14'
            ],
            borderColor: '#ffffff',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            callbacks: {
              label: (context) => {
                const label = context.label || '';
                const value = context.raw || 0;
                return `${label}: $${value.toLocaleString()}`;
              }
            }
          }
        }
      }
    });
  }


}
