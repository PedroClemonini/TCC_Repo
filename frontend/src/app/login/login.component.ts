import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { catchError } from 'rxjs'; // Para lidar com erros na requisição
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    ReactiveFormsModule,
    CommonModule,
    HttpClientModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm: FormGroup;
  apiUrl = 'http://localhost:8080/CLMES/user?action=login';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const loginData = this.loginForm.value;

      // Enviar os dados para a API com POST
      this.http
        .post(this.apiUrl, loginData)
        .pipe(
          catchError((error) => {
            console.error('Erro na requisição:', error);
            return []; // Retorna um array vazio ou trate o erro de outra maneira
          }),
        )
        .subscribe((response) => {
          console.log('Resposta da API:', response);
          // Aqui você pode lidar com a resposta, como redirecionar para outra página
        });
    } else {
      console.log('Formulário inválido');
    }
  }
}
