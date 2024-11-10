package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import org.springframework.graphql.data.method.annotation.Argument
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeesRepository.findById(id).orElse(null)
    }

    @MutationMapping
    fun addEmployee(@Argument employee: CreateEmployeeInput): Employee {
        val newEmployee = Employee(
            name = employee.name ?: "",
            dateOfBirth = employee.dateOfBirth ?: "",
            city = employee.city ?: "",
            salary = employee.salary ?: 0.0f,
            gender = employee.gender ?: "",
            email = employee.email ?: ""
        )
        return employeesRepository.save(newEmployee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id)
            return true
        }
        return false
    }

    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument employee: CreateEmployeeInput): Employee? {
        val existingEmployee = employeesRepository.findById(id).orElse(null) ?: return null
        existingEmployee.apply {
            name = employee.name ?: this.name
            dateOfBirth = employee.dateOfBirth ?: this.dateOfBirth
            city = employee.city ?: this.city
            salary = employee.salary ?: this.salary
            gender = employee.gender ?: this.gender
            email = employee.email ?: this.email
        }
        return employeesRepository.save(existingEmployee)
    }
}

