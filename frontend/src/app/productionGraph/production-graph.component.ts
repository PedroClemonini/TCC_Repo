import { Component, OnInit, ElementRef } from '@angular/core';
import * as d3 from 'd3';
import { FormularioComponent } from '../formulario/formulario.component';
import { MachineService } from '../services/machine.service';
import { Machine } from '../services/machine';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-production-graph',
  standalone: true,
  imports: [FormularioComponent],
  templateUrl: './production-graph.component.html',
  styleUrl: './production-graph.component.css',
})
export class ProductionGraphComponent implements OnInit {
  dados: any = null;
  machineId: number = 0;
  constructor(
    private el: ElementRef,
    private formularioService: MachineService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.machineId = params['machine_id'];
    });

    this.formularioService.formularioData$.subscribe((dados) => {
      this.dados = dados;
      if (this.dados) {
        this.createChart(this.dados);
      }
    });
  }
  private createChart(data: Machine[]): void {
    const width = 928;
    const height = 500;
    const marginTop = 50;
    const marginRight = 30;
    const marginBottom = 70;
    const marginLeft = 80;

    const svgContainer = d3
      .select(this.el.nativeElement)
      .select('.chart-container');

    svgContainer.selectAll('*').remove();

    const svg = svgContainer
      .append('svg')
      .attr('width', width)
      .attr('height', height);

    let minMaxDate: Date[] = d3.extent(data, (d) => d.datetimeStart) as [
      Date,
      Date,
    ];

    const xScale = d3.scaleTime(minMaxDate, [marginLeft, width - marginRight]);

    let minMaxAcumulate: number[] = d3.extent(
      data,
      (d): Number => d.acumulateValue,
    ) as [number, number];

    const yScale = d3.scaleLinear(minMaxAcumulate, [
      height - marginBottom,
      marginTop,
    ]);

    const xAxis = d3
      .axisBottom<Date>(xScale)
      .tickFormat(d3.timeFormat('%d/%b'));

    const yAxis = d3
      .axisLeft(yScale)
      .ticks((minMaxAcumulate[1] - minMaxAcumulate[0]) / 5000)
      .tickFormat((d) => `${(<number>d / 1000).toFixed(0)}k`)
      .tickSize(0)
      .tickPadding(10);

    const tooltip = d3
      .select(this.el.nativeElement)
      .append('div')
      .attr('class', 'tooltip')
      .style('background-color', 'rgba(0, 0, 0, 0.7)')
      .style('color', 'white')
      .style('padding', '5px')
      .style('border-radius', '5px')
      .style('font-size', '12px')
      .style('opacity', 100);


    svg
      .append('g')
      .attr('transform', `translate(0,${height - marginBottom})`)
      .style('font-size', '14px')
      .call(xAxis)
      .call((g) => g.select('.domain').remove())
      .selectAll('.tick line')
      .style('stroke-opacity', 0);
    svg.selectAll('.tick text').attr('fill', '#777');

    svg
      .append('g')
      .attr('transform', `translate(${marginLeft},0)`)
      .style('font-size', '14px')
      .call(yAxis)
      .call((g) => g.select('.domain').remove())
      .selectAll('.tick line')
      .style('stroke-opacity', 0);

    svg
      .selectAll('xGrid')
      .data(xScale.ticks().slice(1))
      .join('line')
      .attr('x1', (d) => xScale(d))
      .attr('x2', (d) => xScale(d))
      .attr('y1', marginTop)
      .attr('y2', height - marginBottom)
      .attr('stroke', '#e0e0e0')
      .attr('stroke-width', 0.5);

    svg
      .selectAll('yGrid')
      .data(
        yScale.ticks((minMaxAcumulate[1] - minMaxAcumulate[0]) / 5000).slice(1),
      )
      .join('line')
      .attr('x1', marginLeft)
      .attr('x2', width - marginRight)
      .attr('y1', (d) => yScale(d))
      .attr('y2', (d) => yScale(d))
      .attr('stroke', '#e0e0e0')
      .attr('stroke-width', 0.5);

    const line = d3
      .line<any>()
      .x((d) => xScale(d.datetimeStart)!) // Coordenada X da linha
      .y((d) => yScale(d.acumulateValue)); // Coordenada Y da linha

    svg
      .append('path')
      .data([data]) // O caminho é definido por um array de dados
      .attr('fill', 'none') // Sem preenchimento
      .attr('stroke', 'teal') // Cor da linha
      .attr('stroke-width', 2) // Largura da linha
      .attr('d', line); // Aplica a função `line` aos dados

    const circle = svg
      .append('circle')
      .attr('r', 0)
      .attr('fill', 'steelblue')
      .style('stroke', 'white')
      .attr('opacity', 0.7)
      .style('pointer-events', 'none');

    const listeningRect = svg
      .append('rect')
      .attr('width', width)
      .attr('height', height)
      .attr('fill', 'none')
      .attr('pointer-events', 'all');

    listeningRect.on('mousemove', function(event) {
      const [xCoord] = d3.pointer(event, this);
      const bisectDate = d3.bisector((d: Machine) => d.datetimeStart).left;
      const x0 = xScale.invert(xCoord);
      const i = bisectDate(data, x0, 1);
      const d0 = data[i - 1];
      const d1 = data[i];
      const d =
        x0.getTime() - d0.datetimeStart.getTime() >
          d1.datetimeStart.getTime() - x0.getTime()
          ? d1
          : d0;
      const xPos = xScale(d.datetimeStart);
      const yPos = yScale(d.acumulateValue);

      circle.attr('cx', xPos).attr('cy', yPos);

      circle.transition().duration(50).attr('r', 5);
      const svgRect = svg.node()!.getBoundingClientRect();

      tooltip
        .style('display', 'block')
        .style('position', 'absolute')
        .style('left', `${svgRect.left + xPos}px`)
        .style('top', `${svgRect.top + yPos}px`)
        .html(`<strong>Data:</strong> ${d.datetimeStart.toLocaleDateString()}<br><strong>Hora:</strong>${d.datetimeStart.toLocaleTimeString()}<br><strong>Produção acumulada:</strong>
            ${d.acumulateValue !== undefined ? <number>d.acumulateValue : 'N/A'}`);
    });

    listeningRect.on('mouseleave', function() {
      circle.transition().duration(50).attr('r', 0);

      tooltip.style('display', 'none');
    });
    svg
      .append('text')
      .attr('x', -height / 2)
      .attr('y', 20)
      .attr('transform', 'rotate(-90)')
      .attr('text-anchor', 'middle')
      .attr('font-size', '14px')
      .attr('fill', '#555')
      .text('Valor Acumulado (em milhares)');

    svg
      .append('text')
      .attr('x', width / 2)
      .attr('y', height - 10)
      .attr('text-anchor', 'middle')
      .attr('font-size', '14px')
      .attr('fill', '#555')
      .text('Data');
  }
}
