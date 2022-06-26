import { Component, OnInit } from '@angular/core';
import { StompServiceService } from 'src/app/services/stomp/stomp-service.service';

@Component({
  selector: 'app-demandes',
  templateUrl: './demandes.component.html',
  styleUrls: ['./demandes.component.scss'],
})
export class DemandesComponent implements OnInit {
  constructor(private stompService: StompServiceService) {}

  ngOnInit(): void {
    this.stompService.subscribe('/topic/update', () => {
      console.log('some changement ');
    });
  }
}
