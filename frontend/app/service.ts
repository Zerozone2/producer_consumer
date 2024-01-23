import { Injectable } from "@angular/core"
import { HttpClient } from '@angular/common/http';
import { object_shape } from './object_shape';
import { Observable } from "rxjs";

// import {class you made} 
@Injectable({
  providedIn: 'root'
})


export class service {
  //private apiServerUrl = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  public  add_Q(added:any){
    return this.http.post<any>(`http://localhost:8080/add_Q/${added}`,null);
  }
  public  add_M(added:any){
    return this.http.post<any>(`http://localhost:8080/add_M/${added}`,null);
  }
  public  add_link(to:any,from:any){
    return this.http.post<any>(`http://localhost:8080/link/${from}/${to}`,null);
  }
  

  public  run(color:any){
    return this.http.post<any>(`http://localhost:8080/run`, color);
    }
  public  notify(){
      return this.http.get<any>(`http://localhost:8080/notify`);
      }
  public  start(){
        return this.http.post<any>(`http://localhost:8080/appStart`,null);
        }
  public  clear(){
         return this.http.get<any>(`http://localhost:8080/clear`);
          }
  public  re_run(){
            return this.http.get<any>(`http://localhost:8080/re_run`);
            }
}