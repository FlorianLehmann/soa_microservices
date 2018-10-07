package Uberoo.repository;

import Uberoo.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long>
{

}