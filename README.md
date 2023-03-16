# Logistics-Application-repository

Partial development of a complex logistics application with busines logic and REST layers.
This application is able to make Transportation plans which includes sections and milestones with related timing.

- Transportaion plan contains the estimated income in case of successful delivery on time, and sections
- Section represents a transportation section consists of start and end milestones
- Milestone refers to an address and a planned time which defines the deadline to reach that address
- Address contains all info related to the destination and milestone

You can add new adresses, searching addresses by id, finding all and deleting them by id and modifying them.
Application allows to search dynamically based on criterias using specification and supports pageable function as well.
The application able to register delays in minutes means increasing the planned times in case of from and to milestones.
The delay time affects the estimated income reducing that with certain precents based on 30, 60, 120 mins.
The application uses authentication and authorization via spring security providing a POST end point where the user can log in
using username and password. In case of correct username and password the application gives a JWT token which is valid during 10 minutes.
Users have access to certain end points as AddressManager or TransportManager roles and authorities.



