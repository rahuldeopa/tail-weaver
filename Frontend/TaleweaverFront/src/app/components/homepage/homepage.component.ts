import { Component } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { MatButton, MatButtonModule, MatFabButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [FooterComponent,MatButton,MatButtonModule,RouterLink,MatFabButton],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {

}
