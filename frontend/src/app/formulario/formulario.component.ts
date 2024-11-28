import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
  signal,
} from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MachineService } from '../services/machine.service';
import { HttpClientModule } from '@angular/common/http';
import {
  MatDatepickerInputEvent,
  MatDatepickerModule,
} from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatDatepickerModule,
  ],
  templateUrl: './formulario.component.html',
  styleUrl: './formulario.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FormularioComponent implements OnInit {
  @Input() machineId: number = 0;
  events = signal<string[]>([]);

  range = new FormGroup({
    dataInicio: new FormControl<Date>(new Date()),
    dataFim: new FormControl<Date>(new Date()),
  });

  constructor(private formService: MachineService) { }
  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    this.events.update((events) => [...events, `${type}: ${event.value}`]);
    this.onSubmit();
  }
  ngOnInit(): void {
    this.onSubmit();
  }

  onSubmit() {
    const dados = this.range.value;
    dados.dataFim = <Date>dados.dataFim;
    dados.dataInicio = <Date>dados.dataInicio;

    const dadosFormatados = {
      id: this.machineId,
      dataInicio: new Date(dados.dataInicio.setHours(0, 0, 0, 0)).toISOString(),
      dataFim: new Date(dados.dataFim.setHours(23, 59, 59, 59)).toISOString(),
    };
    console.log(dadosFormatados);
    this.formService.getData(dadosFormatados);
  }
}
