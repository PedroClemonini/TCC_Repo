export class Machine {
datetimeStart:Date;
productionLog:Number;
acumulateValue:Number;
  constructor(d:Date,prLog:Number,acV:Number){
   this.datetimeStart =d;
    this.productionLog = prLog;
    this.acumulateValue = acV;
  }

}
