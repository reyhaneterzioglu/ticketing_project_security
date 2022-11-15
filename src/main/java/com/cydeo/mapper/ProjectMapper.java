package com.cydeo.mapper;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjectMapper {
    private final ModelMapper modelMapper;

    public Project convertToEntity(ProjectDTO dto) {

        return modelMapper.map(dto, Project.class);
    }

    public ProjectDTO convertToDto(Project entity) {

        return modelMapper.map(entity, ProjectDTO.class);
    }
}
