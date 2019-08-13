package com.spaceboost.challenge.model.campaign;

import com.spaceboost.challenge.model.adGroup.AdGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    private int id;

    private List<AdGroup> adGroups;

}
