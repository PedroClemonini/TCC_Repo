import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProductionGraphComponent } from './productionGraph/production-graph.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { GeneralStatisticsComponent } from './component/general-statistics/general-statistics.component';
import { MachineListComponent } from './component/machine-list/machine-list.component';
export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, children: [
      { path: 'overview', component: GeneralStatisticsComponent },
      { path: 'machines', component: MachineListComponent},
      { path: 'graph', component: ProductionGraphComponent }
    ],
  },


];
