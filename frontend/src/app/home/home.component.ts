import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { HeaderComponent } from '../general/header/header.component';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet,RouterLink, RouterLinkActive,HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
