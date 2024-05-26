import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = "http://localhost:8080"

  constructor(private httpClient:HttpClient,private router:Router) { }

  authSubject = new BehaviorSubject<any>({
    user:null
  })

  login(userData:any):Observable<any>{
    return this.httpClient.post<any>(`${this.baseUrl}/auth/signin`,userData)
  }

  register(userData:any):Observable<any>{
    return this.httpClient.post<any>(`${this.baseUrl}/auth/signup`,userData)
  }

  getUserProfile()
  {
    const headers = new HttpHeaders(  

      { Authorization : `Bearer ${localStorage.getItem('jwt')}`}
    )

    return this.httpClient.get(`${this.baseUrl}/api/user_profile`,{headers}).pipe(
      tap((user)=>{
        const currentState = this.authSubject.value
        this.authSubject.next({...currentState,user})
      })
    )
  }
 

  logout()
  {
    localStorage.clear()
     
    this.authSubject.next({})

    this.router.navigateByUrl("/auth");
  }
}
