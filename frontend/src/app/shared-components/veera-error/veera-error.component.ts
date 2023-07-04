import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-veera-error',
  templateUrl: './veera-error.component.html',
  styleUrls: ['./veera-error.component.scss']
})
export class VeeraErrorComponent {

  @Input() message!: string;

}
