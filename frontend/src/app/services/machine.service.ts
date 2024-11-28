import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError } from 'rxjs';
import { Machine } from './machine';

@Injectable({
  providedIn: 'root',
})
export class MachineService {
  private formularioData = new BehaviorSubject<any>(null); // Armazena os dados do formulário
  formularioData$ = this.formularioData.asObservable();

  private readonly apiUrl = 'http://localhost:8080/CLMES/Machine';

  constructor(private http: HttpClient) {}

  setFormularioData(data: any[]): void {
    this.formularioData.next(data);
  }

  getData(dados: any) {
    const params = {
      machine_id: dados.id,
      start_date: dados.dataInicio,
      end_date: dados.dataFim,
    };

    this.http
      .get<any[]>(this.apiUrl, { params })
      .pipe(
        catchError((error) => {
          console.error('Erro na requisição:', error);
          return [];
        }),
      )
      .subscribe((response) => {
        this.setFormularioData(this.dataHandler(response));
      });
  }

  public dataHandler(data: any[]): Machine[]{
    let acumulatedValue = 0;
    const parsedData = data.map((d) => {
      const cleanedDatetime = d.datetimeStart
        .trim()
        .replace(/\u200B/g, '')
        .replace(/\u00A0/g, ''); // Remove caracteres indesejados

      const parsedDate = new Date(cleanedDatetime);
      acumulatedValue += d.productionLog;
      return new Machine(parsedDate,d.productionLog,acumulatedValue);
    });
    return parsedData;
  }
}
