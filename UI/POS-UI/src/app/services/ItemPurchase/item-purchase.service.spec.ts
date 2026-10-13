import { TestBed } from '@angular/core/testing';

import { ItemPurchaseService } from './item-purchase.service';

describe('ItemPurchaseService', () => {
  let service: ItemPurchaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemPurchaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
