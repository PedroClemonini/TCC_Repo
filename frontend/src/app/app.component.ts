import { Component } from '@angular/core';
import { HomeComponent } from './home/home.component';
import { RouterOutlet,RouterLink,RouterLinkActive } from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink, RouterLinkActive, HomeComponent, MatToolbarModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
