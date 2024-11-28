import { Machine } from './machine';

describe('Machine', () => {
  it('should create an instance', () => {
    expect(new Machine(new Date('2024-11-27T11:20:00'), 10, 100)).toBeTruthy();
  });
});
