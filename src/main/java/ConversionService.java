import noNamespace.RootDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private final CarMapper mapper;

    public ConversionService(@Autowired CarMapper mapper) {
        this.mapper = mapper;
    }

    public void convert() {

        final Car car = new Car();
        car.setMake("Morris");
        car.setNumberOfSeats(5);

        CarDto carDto = mapper.carToCarDto(car);

        RootDocument document;

        System.exit(0);
    }
}
