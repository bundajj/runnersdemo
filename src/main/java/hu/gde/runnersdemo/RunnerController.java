package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class RunnerController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    @Autowired
    public RunnerController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
    }

    @GetMapping("/runner")
    RunnerEntity getRunner(@RequestParam (value= "searchString", defaultValue = "") String searchString){
        RunnerEntity runnerEntity = new RunnerEntity();

        runnerEntity.setRunnerId(1);
        runnerEntity.setRunnerName("Tomi");
        runnerEntity.setAveragePace(310);


        LapTimeEntity laptime1 = new LapTimeEntity();
        laptime1.setLapNumber(1);
        laptime1.setTimeSeconds(120);
        laptime1.setRunner(runnerEntity);

        LapTimeEntity laptime2 = new LapTimeEntity();
        laptime2.setLapNumber(2);
        laptime2.setTimeSeconds(110);
        laptime2.setRunner(runnerEntity);
        runnerEntity.getLaptimes().add(laptime1);
        runnerEntity.getLaptimes().add(laptime2);

        runnerRepository.save(runnerEntity);

        return runnerEntity;
    }
    @GetMapping("/runner/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }




        }

    @GetMapping("/runner/{id}/maximum")
    public double getMaxlaptime(@PathVariable Long id){

        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int maxLaptime = 0;
            for (LapTimeEntity laptime : laptimes) {
                if(laptime.getTimeSeconds()>maxLaptime){

                    maxLaptime=laptime.getTimeSeconds();
                }
            }

            return maxLaptime;
        } else {
            return -1.0;
        }

    }


}
