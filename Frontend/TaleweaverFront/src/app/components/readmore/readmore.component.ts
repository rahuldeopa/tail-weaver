import { Component , Inject} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-readmore',
  standalone: true,
  imports: [MatDialogModule,MatButtonModule],
  templateUrl: './readmore.component.html',
  styleUrl: './readmore.component.scss'
})
export class ReadmoreComponent {
  constructor(
    public dialogRef: MatDialogRef<ReadmoreComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onClose(): void {
    this.dialogRef.close();
  }
}
