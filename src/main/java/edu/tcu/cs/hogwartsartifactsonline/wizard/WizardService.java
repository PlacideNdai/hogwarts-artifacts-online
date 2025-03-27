package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WizardService {


    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }


    public List<Wizard> findAllWizards() {
        return wizardRepository.findAll();
    }

    public Wizard findById(int wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(()-> new ObjectNotFoundException("Wizard",wizardId) );
    }


    public Wizard save(Wizard wizard) {
        return this.wizardRepository.save(wizard);
    }

    public Wizard update(Integer wizardID, Wizard updatedWizard) {
        return this.wizardRepository.findById(wizardID)
                .map(oldWizard ->{
                    oldWizard.setName(updatedWizard.getName());
                    return this.wizardRepository.save(oldWizard);
                }).orElseThrow(()-> new ObjectNotFoundException("Wizard",wizardID));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = wizardRepository.findById(wizardId)
                .orElseThrow(()-> new ObjectNotFoundException("Wizard",wizardId));
        wizardToBeDeleted.removeAllArtifact();
        this.wizardRepository.deleteById(wizardId);
    }

}
