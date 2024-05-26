import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StoryService {

  private baseUrl = "http://localhost:8080"
  constructor(private httpClient:HttpClient,private router:Router) { }

  storySubject = new BehaviorSubject<any>({
    story:null
  })

  getstory(prompt:string)
  {
    let headers = new HttpHeaders(  

      { Authorization : `Bearer ${localStorage.getItem('jwt')}`}
    )

    return this.httpClient.get(`${this.baseUrl}/api/story/generate/${prompt}`,{headers}) 
      
   
  }

  saveStory(story:any)
  {
    let headers = new HttpHeaders(  

      { Authorization : `Bearer ${localStorage.getItem('jwt')}`}
    )
    return this.httpClient.post(`${this.baseUrl}/api/story`,story,{headers});
  }
  getAllStories(page:number)
  {
    let headers = new HttpHeaders(  

      { Authorization : `Bearer ${localStorage.getItem('jwt')}`}
    )
    return this.httpClient.get(`${this.baseUrl}/api/story/all/${page}`,{headers})
  }
 
  deleteStory(storyid:number)
  {
    let headers = new HttpHeaders(  

      { Authorization : `Bearer ${localStorage.getItem('jwt')}`}
    )
    return this.httpClient.delete(`${this.baseUrl}/api/story/${storyid}`,{headers})
  }
}
