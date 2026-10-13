import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Config } from 'src/app/entities/Config';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  config: Config = new Config;
    
  constructor(private http: HttpClient) {}

  loadConfig() {
    return this.http
      .get<Config>('./assets/config.json')
      .toPromise()
      .then(config => {
        this.config = config;
      });
  }
}
