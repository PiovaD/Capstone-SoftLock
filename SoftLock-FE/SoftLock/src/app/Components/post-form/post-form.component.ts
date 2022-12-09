import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.scss']
})
export class PostFormComponent implements OnInit {

  @Input() postForm!: FormGroup;
  @Input() hasTitle: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

}
