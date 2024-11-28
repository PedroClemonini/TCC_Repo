import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionGraphComponent } from './production-graph.component';

describe('ProductionGraphComponent', () => {
  let component: ProductionGraphComponent;
  let fixture: ComponentFixture<ProductionGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductionGraphComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductionGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
