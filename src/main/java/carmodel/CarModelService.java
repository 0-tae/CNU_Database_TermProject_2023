package carmodel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarModelService {
    private final CarModelRepository carModelRepository;

    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public List<CarModel> findAllCarModelByVehicleType(String vehicleType){
        List<CarModel> carModels = carModelRepository.findAll();
        List<CarModel> filteredList = new ArrayList<>();

        for(CarModel carModel : carModels){
            if(carModel.getVehicleType().equals(vehicleType)){
                filteredList.add(carModel);
            }
        }

        return filteredList;
    }

}
