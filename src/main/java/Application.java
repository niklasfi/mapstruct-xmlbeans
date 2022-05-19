public class Application {

    public static void main(String[] args) {
        final Car car = new Car();
        car.setMake("Morris");
        car.setNumberOfSeats(5);

        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        System.exit(0);
    }
}
