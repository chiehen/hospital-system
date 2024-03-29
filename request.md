# Design for Special Request

## Model

Create a a New JPA Entity Visit with the attribute: id, visitDate, patient(fk), hospital(fk).

Also, add visitdate as index column.


## Query
create a Query that caculates the patients' average age whose the visit date is between a period and sex is the designed sex.

## grpc
return the message type 'ageDatas'
```
message AgeData {
    String month = 1;
    SexType sex = 2;
    int32 age = 3;
}

message AgeDatas {
    repeated AgeData agedatas = 1;
}

```



