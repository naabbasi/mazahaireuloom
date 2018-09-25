import { MyMaterialModule } from './my-material.module';

describe('MyMaterialModule', () => {
  let myMaterialModule: MyMaterialModule;

  beforeEach(() => {
    myMaterialModule = new MyMaterialModule();
  });

  it('should create an instance', () => {
    expect(myMaterialModule).toBeTruthy();
  });
});
