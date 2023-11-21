import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'limitString'
})
export class LimitStringPipe implements PipeTransform {

  transform(value: String, length: number) :String {
    if (value.length>length){
      value= value.substring(0, length-3)+"...";
    }
    return value;

  }

}
