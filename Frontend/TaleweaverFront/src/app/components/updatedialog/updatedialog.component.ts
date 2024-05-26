import { Component,Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { StoryService } from '../../services/Story/story.service';
import { MatInput } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
@Component({
  selector: 'app-updatedialog',
  standalone: true,
  imports: [MatDialogModule,MatButtonModule,MatInput,CommonModule,FormsModule,MatFormFieldModule],
  templateUrl: './updatedialog.component.html',
  styleUrl: './updatedialog.component.scss'
})
export class UpdatedialogComponent {
  constructor(
    public dialogRef: MatDialogRef<UpdatedialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private storyService : StoryService
  ) {}

  onClose(): void {
    this.dialogRef.close();
  }
  update(story:any)
  {
    this.storyService.saveStory(story).subscribe(
      (data:any)=>{
        this.dialogRef.close()
        alert(data.message);
      }
    )
  }
}
